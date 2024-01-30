import {HOST} from '../../commons/hosts';
import RestApiClient from "../../commons/api/rest-device";


export function delUserId(id) {
    let request1 = new Request(HOST.backend_api1 + endpoint.ui , {
        method: 'DELETE',
        headers : {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem('token'),
        },
        body: id
    });
    console.log("URL: " + request1.url);

    RestApiClient.performRequest(request1,()=>{console.log('success')});
}


export function postUserId(id) {
    let request1 = new Request(HOST.backend_api1 + endpoint.ui , {
        method: 'POST',
        headers : {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem('token'),
        },
        body: id
    });
    console.log("URL: " + request1.url);

    RestApiClient.performRequest(request1,()=>{console.log('success')});
}


export function putDevice(device, callback) {
    let dev = {id:device.id, mhenergyC: parseInt(device.MHEnergyC),address:device.address,description:device.description,userId: device.userId}
    console.log(dev);
    let request = new Request(`${HOST.backend_api1 + endpoint.device}?userId=${device.userId}` , {
        method: 'PUT',
        headers : {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem('token'),
        },
        body: JSON.stringify(dev)
    });

    console.log("URL: " + request.url);

    RestApiClient.performRequest(request, callback);
}


export function delDevice(item, callback) {
    console.log(item);
    let request = new Request(HOST.backend_api1 + endpoint.device , {
        method: 'DELETE',
        headers : {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem('token'),
        },
        body: JSON.stringify(item)
    });

    console.log("URL: " + request.url);

    RestApiClient.performRequest(request, callback);
}


const endpoint = {
    device: '/spring-device/device',
    ui: '/spring-device/userId'
};

function getDevices(callback) {
    let request = new Request(HOST.backend_api1 + endpoint.device, {
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

function getDeviceByUserId(user_id, callback){
    let request = new Request(HOST.backend_api1 + endpoint.device+ '/findBy/' + user_id, {
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

function postDevice(device, callback){
    console.log(typeof (parseInt(device.MHEnergyC)))
    let dev = {mhenergyC: parseInt(device.MHEnergyC),address:device.address,description:device.description,userId: device.userId}
    console.log(dev);
    let request = new Request(`${HOST.backend_api1 + endpoint.device}?userId=${device.userId}`, {
        method: 'POST',
        headers : {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem('token'),
        },
        body: JSON.stringify(dev)
    });

    console.log("URL: " + request.url);

    RestApiClient.performRequest(request, callback);
}

export {
    getDevices,
    getDeviceByUserId,
    postDevice,
};
