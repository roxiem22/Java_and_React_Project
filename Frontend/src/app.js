import React from 'react'
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom'
import Home from './home/home';
import PersonContainer from './person/person-container'

import ErrorPage from './commons/errorhandling/error-page';
import styles from './commons/styles/project-style.css';
import PersonForm from "./person/components/person-form";
import UserForm from "./person/components/person-table";
import Chat from "./person/components/chat";

class App extends React.Component {


    render() {

        return (
            <div className={styles.back}>
            <Router>
                <div>
                    <Switch>

                        <Route
                            exact
                            path='/'
                            render={() => <Home/>}
                        />

                        <Route
                            exact
                            path='/admin'
                            render={() => <PersonForm/>}
                        />

                        <Route
                            exact
                            path='/chat'
                            render={() => <Chat/>}
                        />

                        <Route
                            exact
                            path='/user'
                            render={() => <UserForm/>}
                        />

                        {/*Error*/}
                        <Route
                            exact
                            path='/error'
                            render={() => <ErrorPage/>}
                        />

                        <Route render={() =><ErrorPage/>} />
                    </Switch>
                </div>
            </Router>
            </div>
        )
    };
}

export default App
