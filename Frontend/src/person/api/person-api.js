import {HOST} from '../../commons/hosts';
import RestApiClient from "../../commons/api/rest-client";
import * as API_DEVICES from "./device-api";

export function putUser(user, callback) {
    console.log(user);
    let request = new Request(HOST.backend_api + endpoint.person , {
        method: 'PUT',
        headers : {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem('token'),
        },
        body: JSON.stringify(user)
    });

    console.log("URL: " + request.url);

    RestApiClient.performRequest(request, callback);
}


export function delUser(item, callback) {
    console.log(item);
    let request = new Request(HOST.backend_api + endpoint.person , {
        method: 'DELETE',
        headers : {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem('token'),
        },
        body: JSON.stringify(item)
    });

    console.log("URL: " + request.url);

    RestApiClient.performRequest(request, (result, status, error) => {
        API_DEVICES.delUserId(result);

    });
}


const endpoint = {
    person: '/spring-user/user',
};

function getUsers(callback) {
    console.log(localStorage.getItem('token'))
    let request = new Request(HOST.backend_api + endpoint.person, {
        method: 'GET',
        headers : {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem('token'),
        },
    });
    console.log(request.url);
    RestApiClient.performRequest(request, callback);
}

function getUserById(params, callback){
    let request = new Request(HOST.backend_api + endpoint.person + params.id, {
       method: 'GET'

    });

    console.log(request.url);
    RestApiClient.performRequest(request, callback);
}

function postUser(user, callback){
    let request = new Request(HOST.backend_api + endpoint.person , {
        method: 'POST',
        headers : {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem('token'),
        },
        body: JSON.stringify(user)
    });

    console.log("URL: " + request.url);

    RestApiClient.performRequest(request, (result, status, error) => {
        API_DEVICES.postUserId(result);

    });

}

function login(user, callback) {
    console.log('User:', user);
    let credentials = { name: user.name, password: user.password };
    let request = new Request(HOST.backend_api +  endpoint.person + '/login', {
        method: 'POST',
        headers : {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(credentials)
    });
    console.log("URL: " + request.url);

    RestApiClient.performRequest(request, callback);
}

export {
    getUsers,
    getUserById,
    postUser,
    login
};
