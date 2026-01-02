import axios from 'axios';

// Check if we're using ngrok
const isNgrok = import.meta.env.VITE_API_BASE_URL.includes('ngrok');

const api = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
    ...(isNgrok && {
      'ngrok-skip-browser-warning': 'true',
      'Bypass-Tunnel-Reminder': 'true',
    }),
  },
});

// Add request interceptor for auth token
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

// Add response interceptor
api.interceptors.response.use(
  (response) => response.data,
  (error) => {
    const message = error.response?.data?.message || error.message;
    
    // Log ngrok-specific errors
    if (isNgrok && error.code === 'CERT_HAS_EXPIRED') {
      console.error('Ngrok SSL issue detected. Ask your friend to restart ngrok.');
    }
    
    console.error('API Error:', message);
    return Promise.reject(error);
  }
);

export default api;