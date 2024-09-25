import React from "react";
import "../../styles/admin/Slide.css"; // Updated import statement

const Sidebar = ({ onOptionClick }) => {
  return (
    <div className="sidebar">
      <h2>Admin-Dashboard</h2>
      <ul>
        <li onClick={() => onOptionClick("Customer Statistics")}>
          Customer Statistics
        </li>
        <li onClick={() => onOptionClick("Deactivated Users")}>
          Deactivated Users
        </li>
        <li onClick={() => onOptionClick("Activated Users")}>
          Activated Users
        </li>
        <li onClick={() => onOptionClick("Delete Users")}>
          Delete Users
        </li>
      </ul>
    </div>
  );
};

export default Sidebar;
