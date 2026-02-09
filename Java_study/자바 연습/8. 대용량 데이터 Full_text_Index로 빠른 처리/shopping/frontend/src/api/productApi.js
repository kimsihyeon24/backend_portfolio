import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api/products';

export const fetchProductsApi = async (keyword, page, size = 10) => {
  const url = keyword 
    ? `${API_BASE_URL}/search?keyword=${keyword}&page=${page}&size=${size}`
    : `${API_BASE_URL}?page=${page}&size=${size}`;
  
  const response = await axios.get(url);
  return response.data; 
};