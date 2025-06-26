import { p } from "framer-motion/client";
import React from "react";

function ProductCard({ product }) {
  return (
    <div style={{
      border: "1.5px solid #ddd",
      borderRadius: "9px",
      padding: "50px",
      margin: "15px",
      width: "300px",
      backgroundColor: "#fff",
      boxShadow: "0 2px 5px rgba(0,0,0,0.1)"
      
    }}>
             {product.image.length > 0 ? (
              <img
                src={`http://localhost:9090/coldMarket/images/image/download/${product.image[0].id}`}
                alt={product.name}
                style={{ width: "100%", height: "auto" }}
              />
            ) : (
              <p style={{fontWeight:"bold", fontFamily:"sans-serif"}}>No image available</p>
            )}
      <h3>{product.name}</h3>
      <p><b>Brand:</b> {product.brand}</p>
      <p><b>Price:</b> ${product.price}</p>
      <p><b>Category:</b> {product.category?.name}</p>
    </div>
  );
}

export default ProductCard;


