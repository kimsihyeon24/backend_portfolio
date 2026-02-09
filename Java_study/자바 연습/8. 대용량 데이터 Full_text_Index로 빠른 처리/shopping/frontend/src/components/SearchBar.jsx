import { useState, useEffect } from 'react';
import { useDebounce } from '../hooks/useDebounce';

const SearchBar = ({ onSearch }) => {
  const [text, setText] = useState("");
  const debouncedSearchTerm = useDebounce(text, 500);

  // 1. 자동 검색 로직 (디바운싱)
  useEffect(() => {
    // 2글자 이상일 때만 자동 검색 실행 (서버 부하 방지 및 정확도)
    if (debouncedSearchTerm.trim().length >= 2) {
      onSearch(debouncedSearchTerm);
    } else if (debouncedSearchTerm === "") {
      onSearch(""); // 지웠을 때는 전체 목록 노출
    }
  }, [debouncedSearchTerm]);

  // 2. 수동 검색 로직 (엔터키)
  const handleKeyDown = (e) => {
    if (e.key === 'Enter') {
      onSearch(text);
    }
  };

  return (
    <div style={{ display: 'flex', gap: '10px', marginBottom: '20px' }}>
      <input 
        type="text" 
        value={text}
        onChange={(e) => setText(e.target.value)}
        onKeyDown={handleKeyDown}
        placeholder="검색어를 입력하세요 (2자 이상)"
        style={{ flex: 1, padding: '15px', borderRadius: '8px', border: '2px solid #ddd' }}
      />
      <button 
        onClick={() => onSearch(text)}
        style={{ padding: '0 25px', borderRadius: '8px', background: '#007bff', color: 'white', border: 'none', cursor: 'pointer' }}
      >
        검색
      </button>
    </div>
  );
};

export default SearchBar;