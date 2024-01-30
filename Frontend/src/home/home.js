import React from 'react';

import BackgroundImg from '../commons/images/login.jpg';
import {BrowserRouter, Link, Redirect, Route} from 'react-router-dom';
import {Button, Container, Jumbotron} from 'reactstrap';
import * as API_USERS from "../person/api/person-api";
import PersonForm from "../person/components/person-form";
import router from "react-router-dom/Router";
import route from "react-router-dom/Route";

const backgroundStyle = {
    backgroundPosition: 'center',
    backgroundSize: 'cover',
    backgroundRepeat: 'no-repeat',
    width: "100%",
    height: "1920px",
    backgroundImage: `url(${BackgroundImg})`
};

class Home extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            data: null,
            name: '',
            redirectTo: null,
            password: '',
            ok: '',
            errorMessage: ''
        };
    }
    render() {
        if (this.state.redirectTo) {
            return <Redirect to={this.state.redirectTo} />;
        }
        return (

            <div>
                <Jumbotron fluid style={backgroundStyle}>
                    <Container fluid>
                        <form>
                            <div>Enter your username and password:</div><br></br>
                        <mat-form-field className="example-full-width" appearance="fill">
                        <mat-label>Username </mat-label>
                        <input placeholder="Enter your name" name="name" id="name" onChange={this.handleNameChange}></input>
                        </mat-form-field><br></br><br></br>
                        <mat-form-field className="example-full-width" appearance="fill">
                        <mat-label> Password </mat-label>
                        <input placeholder="Enter your password" name="password" id="password" onChange={this.handlePasswordChange}></input>
                        </mat-form-field>
                        <p className="lead">
                            <br></br>
                            <Button color="primary" onClick={this.submit}>
                                Login
                            </Button>
                        </p>
                            {this.state.errorMessage && <p>{this.state.errorMessage}</p>}
                        </form>
                    </Container>
                </Jumbotron>
            </div>
        )
    };

    handleNameChange = (event) => {
        this.setState({ name: event.target.value });
    }

    handlePasswordChange = (event) => {
        this.setState({ password: event.target.value });
    }

    submit = (event) => {
        const { name, password } = this.state;
        event.preventDefault();
        let user = {name,password}
        console.log('User:', user);

        return API_USERS.login(user,(result, status, error) =>{
            if (result !== null && status === 200) {
                localStorage.setItem('id',JSON.stringify(result.id));
                localStorage.setItem('token',result.token);
                this.setState({
                    redirectTo: result.role === "ADMIN" ? "/admin" : "/user",
                });

            } else {
                this.setState({
                    errorMessage: "Login failed. Please check your credentials.",
                });
            }
        });
    }

}

export default Home
