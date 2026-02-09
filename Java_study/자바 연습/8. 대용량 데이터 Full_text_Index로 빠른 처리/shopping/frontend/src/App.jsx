import { useState, useEffect } from 'react';
import { fetchProductsApi } from './api/productApi';
import SearchBar from './components/SearchBar';
import ProductList from './components/ProductList';

function App() {
  const [products, setProducts] = useState([]);
  const [page, setPage] = useState(0);
  const [keyword, setKeyword] = useState("");
  const [loading, setLoading] = useState(false);

  const loadData = async (newKeyword = keyword, isNewSearch = false) => {
    setLoading(true);
    const currentPage = isNewSearch ? 0 : page;
    
    try {
      const data = await fetchProductsApi(newKeyword, currentPage);
      console.log("백엔드 응답 전체:", data);
      if (isNewSearch) {
        setProducts(data.content);
        setPage(1);
      } else {
        setProducts(prev => [...prev, ...data.content]);
        setPage(prev => prev + 1);
      }
      setKeyword(newKeyword);
    } catch (error) {
      alert("데이터를 가져오는 중 오류가 발생했습니다.");
    }
    setLoading(false);
  };

  useEffect(() => { loadData("", true); }, []);

  return (
    <div style={{ padding: '40px', backgroundColor: '#f4f7f6', minHeight: '100vh' }}>
      <header style={{ textAlign: 'center', marginBottom: '40px' }}>
        <h1>대용량 커머스</h1>
        <p>MySQL Full-Text Index 기반 초고속 검색</p>
      </header>
      
      <main style={{ maxWidth: '1000px', margin: '0 auto' }}>
        <SearchBar onSearch={(k) => loadData(k, true)} />
        <ProductList 
          products={products} 
          loading={loading} 
          onLoadMore={() => loadData(keyword, false)} 
        />
      </main>
    </div>
  );
}

export default App;