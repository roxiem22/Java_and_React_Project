import React from 'react';
import { Container, Jumbotron} from 'reactstrap';
import * as API_USERS from "../api/person-api";
import * as API_DEVICES from "../api/device-api";
import BackgroundImg from "../../commons/images/login.jpg";

const backgroundStyle = {
    backgroundPosition: 'center',
    backgroundSize: 'cover',
    backgroundRepeat: 'no-repeat',
    width: "100%",
    height: "1920px",
    backgroundImage: `url(${BackgroundImg})`
};

class PersonForm extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            list: [],
            dlist: [],
            isLoaded: false,
            isLoadedd: false,
            name: '',
            password: '',
            mail: '',
            role: '',
            MHEnergyC: '',
            address: '',
            description: '',
            userId: '',
            id: 0
        };

        this.fetchPersons = this.fetchPersons.bind(this); // Bind the method
        this.fetchDevices = this.fetchDevices.bind(this); // Bind the method
    }

    componentDidMount() {
        // Fetch data when the component mounts
        this.fetchPersons();
        this.fetchDevices();
    }

    fetchPersons() {
        return API_USERS.getUsers((result, status, err) => {
            if (result !== null && status === 200) {
                this.setState({
                    list: result,
                    isLoaded: true,
                    errorStatus: 0,
                    error: null
                });
            } else {
                this.setState(({
                    errorStatus: status,
                    error: err
                }));
            }
        });
    }

    fetchDevices() {
        return API_DEVICES.getDevices((result, status, err) => {
            if (result !== null && status === 200) {
                this.setState({
                    dlist: result,
                    isLoadedd: true,
                    errorStatus: 0,
                    error: null
                });

            } else {
                this.setState(({
                    errorStatus: status,
                    error: err
                }));
            }
        });
    }

    deleteDevice (item) {
        return API_DEVICES.delDevice(item,(result, status, error) => {
            if (result !== null && status === 200) {
                this.setState({
                    alist: result,
                    isLoaded: true
                });

            } else {
                this.setState(({
                    errorStatus: status,
                    error: error
                }));
            }
        });
    }

    deleteUser (item) {
        console.log('User');
        return API_USERS.delUser(item,()=>{console.log('success');})
    }

    updateUser(id){
        const { name, mail, password, role } = this.state;
        //event.preventDefault();
        let user = {id,name,mail,password,role}
        console.log('User:', user);
        return API_USERS.putUser(user,(result, status, error) => {
            if (result !== null && status === 200) {
                this.setState({
                    alist: result,
                    isLoaded: true
                });

            } else {
                this.setState(({
                    errorStatus: status,
                    error: error
                }));
            }
        });
    }

    updateDevice(id){
        const { description, address, MHEnergyC, userId} = this.state;
        let device = {id, description, address, MHEnergyC, userId}
        console.log('Device:', device);
        return API_DEVICES.putDevice(device,(result, status, error) => {
            if (result !== null && status === 200) {
                this.setState({
                    alist: result,
                    isLoaded: true
                });

            } else {
                this.setState(({
                    errorStatus: status,
                    error: error
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
                                {this.state.list.map(item => (
                                    <li key={item.id}>{item.id}, {item.name}, {item.mail}, {item.password}, {item.role} :
                                        <button onClick={() => this.deleteUser(item)}>Delete User</button>
                                        <button onClick={() => this.updateUser(item.id)}>Update User</button>
                                    </li>
                                ))}
                            </ul>
                        </div>
                        <div>
                            <ul>
                                {this.state.dlist.map(item1 => (
                                    <li key={item1.id}>{item1.id}, {item1.description}, {item1.address}, {item1.mhenergyC}, {item1.user_id} :
                                        <button onClick={() => this.deleteDevice(item1)}>Delete Device</button>
                                        <button onClick={() => this.updateDevice(item1.id)}>Update Device</button>
                                    </li>
                                ))}
                            </ul>
                        </div>
                        <div>

                            <h3>User Management</h3>
                            <div>Name:
                                <input placeholder="Enter name" name="name" id="name" onChange={this.handleName}/>
                            </div>
                            <br></br>
                            <div>Email :
                                <input placeholder="Enter mail" name="mail" id="mail" onChange={this.handleMail}/>
                            </div>
                            <br></br>
                            <div>Passw:
                                <input placeholder="Enter password" name="password" id="password"
                                       onChange={this.handlePassword}/>
                            </div>
                            <br></br>
                            <div>URole:
                                <input placeholder="Enter role" name="role" id="role" onChange={this.handleRole}/>
                            </div>
                            <br></br>
                            <button onClick={this.insertUser}>Insert User</button>
                            <h3><br></br>
                                Device Management</h3>
                            <div>Description:
                                <input placeholder="Enter description" name="description" id="description"
                                       onChange={this.handleDescription}/>
                            </div>
                            <br></br>
                            <div> DevAddress:
                                <input placeholder="Enter address" name="address" id="address"
                                       onChange={this.handleAddress}/>
                            </div>
                            <br></br>
                            <div> MHEnergyC:
                                <input placeholder="Enter MHEnergyC" name="MHEnergyC" id="MHEnergyC"
                                       onChange={this.handleMHEnergyC}/>
                            </div>
                            <br></br>
                            <div> CustomerId:
                                <input placeholder="Enter userId" name="userId" id="userId"
                                       onChange={this.handleUserId}/>
                            </div>
                            <br></br>
                            <button onClick={this.insertDevice}>Insert Device</button>
                        </div><br></br>
                        <a href="/chat">Chat With Us</a>
                    </Container>
                </Jumbotron>
            </div>
        ) ;
    }

    handleName = (event) => {
        this.setState({ name: event.target.value });
    }
    handleMail = (event) => {
        this.setState({ mail: event.target.value });
    }
    handleRole = (event) => {
        this.setState({ role: event.target.value });
    }
    handlePassword = (event) => {
        this.setState({ password: event.target.value });
    }

    handleDescription = (event) => {
        this.setState({ description: event.target.value });
    }
    handleAddress = (event) => {
        this.setState({ address: event.target.value });
    }
    handleMHEnergyC = (event) => {
        this.setState({ MHEnergyC: event.target.value });
    }

    handleUserId = (event) => {
        this.setState({ userId: event.target.value });
    }

    insertUser = (event) =>{
        const { name, mail, password, role } = this.state;
        event.preventDefault();
        let user = {name,mail,password,role}
        console.log('User:', user);

        return API_USERS.postUser(user,()=>{console.log('success');});
    }

    insertDevice = (event) =>{
        const { description, address, MHEnergyC, userId} = this.state;
        event.preventDefault();
        let device = {description, address, MHEnergyC, userId}
        console.log('Device:', device);
        return API_DEVICES.postDevice(device,(result, status, error) => {
            if (result !== null && status === 200) {
                this.setState({
                    dlist: result,
                    isLoaded: true
                });

            } else {
                this.setState(({
                    errorStatus: status,
                    error: error
                }));
            }
        });
    }

}

export default PersonForm;
