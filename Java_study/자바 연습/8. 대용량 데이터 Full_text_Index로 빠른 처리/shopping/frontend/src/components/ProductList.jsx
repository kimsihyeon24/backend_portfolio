import { useEffect, useRef } from 'react';
import ProductItem from './ProductItem';

const ProductList = ({ products, loading, onLoadMore }) => {
  // 바닥을 감지할 타겟
  const observerRef = useRef();

  useEffect(() => {
    // 1. 감시 장치(Intersection Observer) 생성
    const observer = new IntersectionObserver(
      (entries) => {
        // 장치가 화면에 보이고(isIntersecting), 로딩 중이 아닐 때만 다음 페이지 호출
        if (entries[0].isIntersecting && !loading) {
          onLoadMore();
        }
      },
      { threshold: 1.0 } // 100% 다 보였을 때 실행
    );

    // 2. 하단 div 감시 시작
    if (observerRef.current) {
      observer.observe(observerRef.current);
    }

    // 3. 언마운트 시 감시 종료 (메모리 누수 방지)
    return () => observer.disconnect();
  }, [loading, onLoadMore]); // 로딩 상태나 호출 함수가 바뀔 때마다 업데이트

  return (
    <div>
      <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fill, minmax(250px, 1fr))', gap: '20px' }}>
        {/* ?. 사용으로 안전하게 렌더링 */}
        {products?.map((product, index) => (
          <ProductItem key={`${product.id}-${index}`} product={product} />
        ))}
      </div>
      
      {/* 바닥 감지용 타겟: 데이터가 있을 때만 렌더링 */}
      {products?.length > 0 && (
        <div ref={observerRef} style={{ height: '50px', textAlign: 'center', marginTop: '20px' }}>
          {loading && <p>🚀 100만 개 데이터 로딩 중...</p>}
        </div>
      )}
    </div>
  );
};

export default ProductList;