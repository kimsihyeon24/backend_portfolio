
const ProductItem = ({ product }) => {
  return (
    <div style={{ 
      border: '1px solid #ddd', 
      padding: '20px', 
      borderRadius: '8px', 
      backgroundColor: 'white',
      boxShadow: '0 2px 4px rgba(0,0,0,0.1)' 
    }}>
      <h3 style={{ fontSize: '1.1rem', margin: '0 0 10px 0' }}>{product.title}</h3>
      <p style={{ color: '#666', marginBottom: '5px' }}>카테고리: {product.category}</p>
      <p style={{ fontWeight: 'bold', color: '#007bff' }}>
        가격: {product.price?.toLocaleString()}원
      </p>
    </div>
  );
};

export default ProductItem;