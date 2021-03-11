import axios from 'axios';

export default axios.create({
  baseURL: `${window.location.protocol}//${window.location.host}/api`,
  headers: {
    'Content-type': 'application/json'
  }
});
