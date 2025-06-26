import React from "react";

function Navbar() {
  return (
    <nav style={{
      backgroundColor: "#222",
      color: "#fff",
      padding: "15px 30px",
      display: "flex",
      justifyContent: "space-between"
    }}>
      <h2>Cold Market</h2>
      <div>
        <a href="#" style={{ color: "#fff", marginRight: "15px" }}>Home</a>
        <a href="#" style={{ color: "#fff" }}>Categories</a>
      </div>
    </nav>
  );
}

export default Navbar;
