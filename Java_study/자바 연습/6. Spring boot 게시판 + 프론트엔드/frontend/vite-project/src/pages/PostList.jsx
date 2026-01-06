import { useState, useEffect } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import api from '../api/axios';

function PostList() {
  const [posts, setPosts] = useState([]);
  const [currentPage, setCurrentPage] = useState(0); 
  const [totalPages, setTotalPages] = useState(0);
  const navigate = useNavigate();

  // 한 번에 보여줄 페이지 번호 개수 (예: 1~5, 6~10)
  const PAGE_GROUP_SIZE = 5;

  useEffect(() => {
    fetchPosts(currentPage);
  }, [currentPage]);

  const fetchPosts = async (page) => {
    try {
      const res = await api.get(`/posts?page=${page}`);
      setPosts(res.data.content || []); 
      setTotalPages(res.data.totalPages || 0);
    } catch (e) {
      console.error("데이터 로드 실패", e);
    }
  };

  // --- [핵심 로직] 현재 페이지가 속한 그룹의 시작과 끝 번호 계산 ---
  const currentGroup = Math.floor(currentPage / PAGE_GROUP_SIZE);
  const startPage = currentGroup * PAGE_GROUP_SIZE;
  const endPage = Math.min(startPage + PAGE_GROUP_SIZE, totalPages);

  return (
    <div style={{ padding: '20px' }}>
      <div style={{ display: 'flex', justifyContent: 'space-between', marginBottom: '20px' }}>
        <h2>📋 게시글 목록</h2>
        <button onClick={() => navigate('/write')} style={btnSuccess}>새 글 쓰기</button>
      </div>

      <table style={tableStyle}>
        <thead style={{ background: '#f8f9fa' }}>
          <tr>
            <th style={{ width: '10%', padding: '12px' }}>번호</th>
            <th style={thStyle}>제목</th>
            <th style={{ width: '20%' }}>작성자</th>
          </tr>
        </thead>
        <tbody>
          {posts.map(post => (
            <tr key={post.id} style={{ borderBottom: '1px solid #eee' }}>
              <td style={{ textAlign: 'center', padding: '12px' }}>{post.id}</td>
              <td style={{ padding: '12px' }}>
                <Link to={`/post/${post.id}`} style={linkStyle}>
                  {post.title} 
                  <span style={{ color: '#007bff', marginLeft: '5px' }}>({post.comments?.length || 0})</span>
                </Link>
              </td>
              <td style={{ textAlign: 'center' }}>{post.author}</td>
            </tr>
          ))}
        </tbody>
      </table>

      {/* --- 개선된 페이지네이션 UI --- */}
      <div style={{ marginTop: '30px', display: 'flex', justifyContent: 'center', alignItems: 'center', gap: '5px' }}>
        
        {/* [처음으로] 버튼 */}
        <button onClick={() => setCurrentPage(0)} disabled={currentPage === 0} style={pageBtn}>«</button>

        {/* [이전 그룹] 버튼 */}
        <button 
          onClick={() => setCurrentPage(startPage - 1)} 
          disabled={startPage === 0}
          style={pageBtn}
        >
          이전
        </button>

        {/* 숫자 번호 버튼 (startPage부터 endPage까지) */}
        {Array.from({ length: endPage - startPage }, (_, i) => startPage + i).map(pageNum => (
          <button 
            key={pageNum} 
            onClick={() => setCurrentPage(pageNum)}
            style={{ 
              ...pageBtn,
              backgroundColor: currentPage === pageNum ? '#007bff' : '#fff',
              color: currentPage === pageNum ? '#fff' : '#000',
              fontWeight: currentPage === pageNum ? 'bold' : 'normal',
              border: currentPage === pageNum ? '1px solid #007bff' : '1px solid #ddd'
            }}
          >
            {pageNum + 1}
          </button>
        ))}

        {/* [다음 그룹] 버튼 */}
        <button 
          onClick={() => setCurrentPage(endPage)} 
          disabled={endPage >= totalPages}
          style={pageBtn}
        >
          다음
        </button>

        {/* [끝으로] 버튼 */}
        <button onClick={() => setCurrentPage(totalPages - 1)} disabled={currentPage === totalPages - 1} style={pageBtn}>»</button>
      </div>
    </div>
  );
}

// 스타일 정의
const tableStyle = { width: '100%', borderCollapse: 'collapse', borderTop: '2px solid #333' };
const thStyle = { width: '50%', textAlign: 'left', padding: '12px' };
const linkStyle = { textDecoration: 'none', color: '#333' };
const btnSuccess = { padding: '8px 16px', background: '#28a745', color: '#fff', border: 'none', borderRadius: '4px', cursor: 'pointer' };
const pageBtn = { padding: '5px 10px', border: '1px solid #ddd', background: '#fff', cursor: 'pointer', borderRadius: '3px', minWidth: '35px' };

export default PostList;