import { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import api from '../api/axios';

function PostWrite() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [post, setPost] = useState({ title: '', content: '', author: '' });

  useEffect(() => {
    if (id) {
      api.get(`/${id}`).then(res => setPost(res.data));
    }
  }, [id]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!post.title || !post.content || !post.author) return alert("모든 항목을 입력하세요.");
    
    try {
      if (id) await api.post(`/update/${id}`, post);
      else await api.post('/write', post);
      navigate('/');
    } catch (e) { alert("저장에 실패했습니다."); }
  };

  return (
    <div style={{ padding: '20px' }}>
      <h2>{id ? "📝 게시글 수정" : "✍️ 새 게시글 작성"}</h2>
      <form onSubmit={handleSubmit}>
        <input 
          placeholder="작성자" 
          value={post.author} 
          onChange={e => setPost({...post, author: e.target.value})}
          disabled={!!id}
          style={inputStyle}
        />
        <input 
          placeholder="제목을 입력하세요" 
          value={post.title} 
          onChange={e => setPost({...post, title: e.target.value})}
          style={inputStyle}
        />
        <textarea 
          placeholder="내용을 입력하세요" 
          value={post.content} 
          onChange={e => setPost({...post, content: e.target.value})}
          style={{ ...inputStyle, height: '300px' }}
        />
        <div style={{ display: 'flex', gap: '10px' }}>
          <button type="submit" style={{ ...btn, background: '#007bff' }}>저장하기</button>
          <button type="button" onClick={() => navigate(-1)} style={{ ...btn, background: '#6c757d' }}>취소</button>
        </div>
      </form>
    </div>
  );
}

const inputStyle = { width: '100%', padding: '12px', marginBottom: '15px', border: '1px solid #ddd', borderRadius: '4px', boxSizing: 'border-box' };
const btn = { flex: 1, padding: '15px', color: '#fff', border: 'none', borderRadius: '4px', cursor: 'pointer', fontSize: '1em' };

export default PostWrite;