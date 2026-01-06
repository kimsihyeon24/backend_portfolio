import { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import api from '../api/axios';

function PostDetail() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [post, setPost] = useState(null);
  const [comments, setComments] = useState([]);
  
  const [newComment, setNewComment] = useState({ writer: '', content: '' });
  const [editingId, setEditingId] = useState(null);
  const [editContent, setEditContent] = useState("");

  useEffect(() => {
    fetchData();
  }, [id]);

  const fetchData = async () => {
    try {
      const pRes = await api.get(`/${id}`);
      const cRes = await api.get(`/comment/${id}`);
      setPost(pRes.data);
      setComments(cRes.data);
    } catch (e) { alert("데이터 로드 실패"); }
  };

  // --- [추가] 게시글 삭제 로직 ---
  const handlePostDelete = async () => {
    if (!confirm("이 게시글을 정말 삭제하시겠습니까? 관련 댓글도 모두 삭제됩니다.")) return;
    try {
      // 백엔드: @DeleteMapping("/delete/{id}") 호출
      await api.delete(`/delete/${id}`); 
      alert("게시글이 삭제되었습니다.");
      navigate('/'); // 삭제 후 목록으로 이동
    } catch (e) {
      alert("게시글 삭제에 실패했습니다.");
    }
  };

  // 댓글 관련 로직들 (등록, 삭제, 수정은 이전과 동일)
  const handleCommentSubmit = async () => {
    if (!newComment.writer || !newComment.content) return alert("내용을 입력하세요.");
    const res = await api.post(`/comment/write/${id}`, newComment);
    setComments(res.data);
    setNewComment({ writer: '', content: '' });
  };

  const handleCommentDelete = async (commentId) => {
    if (!confirm("댓글을 삭제할까요?")) return;
    const res = await api.delete(`/comment/delete/${id}/${commentId}`);
    setComments(res.data);
  };

  const handleCommentUpdate = async (commentId) => {
    try {
      const res = await api.put(`/comment/update/${id}`, { id: commentId, content: editContent });
      setComments(res.data);
      setEditingId(null);
    } catch (e) { alert("수정 실패"); }
  };

  if (!post) return <div style={{ padding: '50px', textAlign: 'center' }}>로딩 중...</div>;

  return (
    <div style={{ padding: '20px' }}>
      <button onClick={() => navigate('/')} style={{ marginBottom: '20px' }}>← 목록으로</button>
      
      <div style={{ borderBottom: '2px solid #333', paddingBottom: '10px' }}>
        <h2>{post.title}</h2>
        <div style={{ display: 'flex', justifyContent: 'space-between', color: '#666' }}>
          <span>작성자: {post.author}</span>
          {/* --- [게시글 제어 버튼 구역 추가] --- */}
          <div>
            <button onClick={() => navigate(`/edit/${id}`)} style={postBtnStyle}>글 수정</button>
            <button onClick={handlePostDelete} style={{ ...postBtnStyle, color: 'red' }}>글 삭제</button>
          </div>
        </div>
      </div>

      <div style={{ padding: '30px 10px', minHeight: '200px', lineHeight: '1.6', whiteSpace: 'pre-wrap' }}>
        {post.content}
      </div>

      {/* 댓글 구역 (이하 동일) */}
      <div style={{ marginTop: '40px' }}>
        <h4>💬 댓글 {comments.length}개</h4>
        <hr />
        {comments.map(c => (
          <div key={c.id} style={{ padding: '15px 0', borderBottom: '1px solid #eee' }}>
            <div style={{ fontWeight: 'bold' }}>{c.writer}</div>
            {editingId === c.id ? (
              <div style={{ marginTop: '10px' }}>
                <textarea value={editContent} onChange={(e) => setEditContent(e.target.value)} style={{ width: '100%', padding: '10px' }} />
                <button onClick={() => handleCommentUpdate(c.id)}>저장</button>
                <button onClick={() => setEditingId(null)}>취소</button>
              </div>
            ) : (
              <div style={{ display: 'flex', justifyContent: 'space-between', marginTop: '5px' }}>
                <span>{c.content}</span>
                <div>
                  <button onClick={() => { setEditingId(c.id); setEditContent(c.content); }} style={smBtn}>수정</button>
                  <button onClick={() => handleCommentDelete(c.id)} style={{ ...smBtn, color: 'red' }}>삭제</button>
                </div>
              </div>
            )}
          </div>
        ))}
      </div>

      {/* 댓글 작성 창 */}
      <div style={{ marginTop: '30px', padding: '20px', background: '#f8f9fa' }}>
        <input placeholder="닉네임" value={newComment.writer} onChange={e => setNewComment({...newComment, writer: e.target.value})} style={{ marginBottom: '10px', padding: '8px' }} />
        <textarea placeholder="댓글을 남겨주세요" value={newComment.content} onChange={e => setNewComment({...newComment, content: e.target.value})} style={{ width: '100%', height: '60px', padding: '10px', marginBottom: '10px' }} />
        <button onClick={handleCommentSubmit} style={{ width: '100%', padding: '10px', background: '#333', color: '#fff' }}>댓글 등록</button>
      </div>
    </div>
  );
}

// 스타일
const postBtnStyle = { marginLeft: '10px', background: 'none', border: '1px solid #ccc', borderRadius: '4px', cursor: 'pointer', padding: '5px 10px' };
const smBtn = { border: 'none', background: 'none', fontSize: '0.85em', cursor: 'pointer', marginLeft: '10px', color: '#666' };

export default PostDetail;