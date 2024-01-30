import React from 'react';
import { Container, Jumbotron } from 'reactstrap';
import * as API_USERS from "../api/person-api";
import * as API_DEVICES from "../api/device-api";
import BackgroundImg from "../../commons/images/login.jpg";
import { connect1, disconnect1, messgs, sendMessage, sendTypingStatus } from "../ws/msg";
import './chat.css';

const backgroundStyle = {
    backgroundPosition: 'center',
    backgroundSize: 'cover',
    backgroundRepeat: 'no-repeat',
    width: "100%",
    height: "1920px",
    backgroundImage: `url(${BackgroundImg})`
};

class Chat extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            chatMessages: [],
            user: '',
            message: '',
            typingUsers: {},
            lastProcessedIndex: -1,
        };
        this.log = this.log.bind(this);
        this.typingTimeouts = {};
    }

    componentDidMount() {
        connect1(this.log, this.handleTypingUpdate);
    }

    log() {
        const newMessages = messgs.slice(this.state.lastProcessedIndex + 1);
        if (newMessages.length > 0) {
            this.setState((prevState) => ({
                chatMessages: [...prevState.chatMessages, ...newMessages],
                lastProcessedIndex: messgs.length - 1,
            }));
        }
    }

    sendMessage = () => {
        sendMessage(`${this.state.user}: ${this.state.message}`, this.log);
        this.clearTypingIndicator();
    };

    handleTypingUpdate = (typingStatus) => {
        const { user, typingUsers } = this.state;

        if (typingStatus.isTyping && typingStatus.user !== user) {
            this.setState((prevState) => ({
                typingUsers: {
                    ...prevState.typingUsers,
                    [typingStatus.user]: true,
                },
            }));

            if (this.typingTimeouts[typingStatus.user]) {
                clearTimeout(this.typingTimeouts[typingStatus.user]);
            }

            this.typingTimeouts[typingStatus.user] = setTimeout(() => {
                this.clearTypingIndicatorForUser(typingStatus.user);
            }, 2000);
        } else if (!typingStatus.isTyping) {
            this.clearTypingIndicatorForUser(typingStatus.user);
        }
    };

    clearTypingIndicator = () => {
        this.setState({ typingUsers: {} });

        Object.values(this.typingTimeouts).forEach((timeout) => {
            clearTimeout(timeout);
        });
    };

    clearTypingIndicatorForUser = (username) => {
        this.setState((prevState) => ({
            typingUsers: {
                ...prevState.typingUsers,
                [username]: false,
            },
        }));

        if (this.typingTimeouts[username]) {
            clearTimeout(this.typingTimeouts[username]);
        }
    };

    componentWillUnmount() {
        disconnect1();
    }

    handleTyping = () => {
        const { user } = this.state;

        if (user) {
            sendTypingStatus(JSON.stringify({ user, isTyping: true }));
        }
    };

    render() {
        const { typingUsers } = this.state;

        return (
            <div>
                <Jumbotron fluid style={backgroundStyle}>
                    <Container fluid>
                        <div>
                            <main className="chat">
                                <div className="container">
                                    <form onSubmit={(e) => { e.preventDefault(); this.sendMessage(); }}>
                                        <div className="chat-send row">
                                            <div className="col-2">
                                                <input
                                                    type="text"
                                                    className="form-control"
                                                    name="user"
                                                    id="input-user"
                                                    placeholder="Your Name"
                                                    value={this.state.user}
                                                    onChange={(e) => this.setState({ user: e.target.value })}
                                                />
                                            </div>
                                            <div className="col-8">
                                                <input
                                                    type="text"
                                                    className="form-control"
                                                    name="message"
                                                    id="input-message"
                                                    placeholder="Type your message..."
                                                    value={this.state.message}
                                                    onChange={(e) => this.setState({ message: e.target.value })}
                                                    onInput={this.handleTyping}
                                                />
                                            </div>
                                            <div className="col-2">
                                                <button type="submit" className="btn btn-primary">
                                                    Send
                                                </button>
                                            </div>
                                        </div>
                                    </form>
                                    {Object.entries(typingUsers).map(([username, isTyping]) => (
                                        isTyping && (
                                            <div key={username} className="typing-indicator">
                                                {`${username} is typing...`}
                                            </div>
                                        )
                                    ))}
                                    <ul className="list-group list-group-flush">
                                        {this.state.chatMessages.map((chatMessage, index) => (
                                            <li className="list-group-item chat-message" key={index}>
                                                {chatMessage}
                                            </li>
                                        ))}
                                    </ul>
                                </div>
                            </main>
                        </div>
                    </Container>
                </Jumbotron>
            </div>
        );
    }
}

export default Chat;