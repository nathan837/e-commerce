import { p } from "framer-motion/client";
import React from "react";

function ProductCard({ product }) {
  return (
    <div style={{
      border: "2px solid #ddd",
      borderRadius: "9px",
      padding: "10px",
      margin: "10px",
      width: "300px",
      backgroundColor: "#fff",
      boxShadow: "0 2px 5px rgba(0,0,0,0.1)"
      
    }}>
             {product.image.length > 0 ? (
              <img
                src={`http://localhost:9090/coldMarket/images/image/download/${product.image[0].id}`}
                alt={product.name}
                style={{ width: "100%", height: "auto", marginTop:"10px"}}
              />
            ) : (
              <p style={{fontWeight:"bold", fontFamily:"sans-serif"}}>No image available</p>
            )}
      <div style={{ border: "1.5px solid #ddd",
                   borderRadius: "9px",
                    padding: "30px",
                    marginBottom:"auto"

      }}>
      <h3>{product.name}</h3>
      <p><b>Brand:</b> {product.brand}</p>
      <p><b>Price:</b> birr-{product.price}</p>
      <p><b>Category:</b> {product.category?.name}</p>
      </div>
    </div>
  );
}

export default ProductCard;


