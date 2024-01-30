import React from 'react';
import { Container, Jumbotron} from 'reactstrap';
import * as API_USERS from "../api/person-api";
import * as API_DEVICES from "../api/device-api";
import BackgroundImg from "../../commons/images/login.jpg";
import {connect,disconnect} from '../ws/notify';
const backgroundStyle = {
    backgroundPosition: 'center',
    backgroundSize: 'cover',
    backgroundRepeat: 'no-repeat',
    width: "100%",
    height: "1920px",
    backgroundImage: `url(${BackgroundImg})`
};

class UserForm extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            list: [],
            isLoaded: false,
        };

        this.fetchDevices = this.fetchDevices.bind(this); // Bind the method
    }

    componentDidMount() {
        // Fetch data when the component mounts
        this.fetchDevices();
        connect();
    }

    componentWillUnmount() {
        disconnect();
    }

    fetchDevices() {
        let user_id = localStorage.getItem('id');
        console.log("it worked"+user_id);
        console.log(typeof(user_id));
        return API_DEVICES.getDeviceByUserId(user_id,(result, status, err) => {
            if (result !== null && status === 200) {
                this.setState({
                    list: result,
                    isLoaded: true,
                    errorStatus: 0,
                    error: null
                });
                console.log(result[0])

            } else {
                this.setState(({
                    errorStatus: status,
                    error: err
                }));
            }
        });
    }


    render() {
        return (
            <div>
                <Jumbotron fluid style={backgroundStyle}>
                    <Container fluid>
                        <div>
                            <ul>
                                {this.state.list.map(item1 => (
                                    <li key={item1.id}>DeviceId: {item1.id},
                                        Description: {item1.description},
                                        Address: {item1.address},
                                        MHEnergyC: {item1.mhenergyC},
                                        UserId: {item1.user_id}
                                    </li>
                                ))}
                            </ul>
                        </div>
                        <br></br>
                        <a href="/chat">Chat With Us</a>
                    </Container>
                </Jumbotron>
            </div>
        ) ;
    }
}

export default UserForm;
