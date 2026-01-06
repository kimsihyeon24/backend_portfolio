// 이 부분이 반드시 있어야 합니다!
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import PostList from './pages/PostList';
import PostWrite from './pages/PostWrite';
import PostDetail from './pages/PostDetail';

function App() {
  return (
    <Router>  {/* 여기서 Router는 BrowserRouter의 별칭입니다 */}
      <div style={{ padding: '20px', maxWidth: '800px', margin: '0 auto' }}>
        <Routes>
          <Route path="/" element={<PostList />} />
          <Route path="/write" element={<PostWrite />} />
          <Route path="/edit/:id" element={<PostWrite />} />
          <Route path="/post/:id" element={<PostDetail />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;