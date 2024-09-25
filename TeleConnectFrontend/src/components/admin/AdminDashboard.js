import React, { useState } from "react";
import "../../styles/admin/Admin.css"; // Add your CSS here
import Sidebar from "./Slider"; // Import the Sidebar component
import ActivatedUsers from "./ActivatedUsers"; // Import ActivatedUsers component
import DeactivatedUsers from "./DeactivatedUser"; // Import DeactivatedUsers component
import CustomerStatistics from "./CustomerStatistics"; // Import CustomerStatistics component
import DeleteUser from "./DeleteUser";

const AdminDashboard = () => {
  const [selectedOption, setSelectedOption] = useState("Customer Statistics");

  // Function to render the selected component
  const renderContent = () => {
    switch (selectedOption) {
      case "Activated Users":
        return <ActivatedUsers />;
      case "Deactivated Users":
        return <DeactivatedUsers />;
      case "Delete Users":
        return <DeleteUser />;
      case "Customer Statistics":
      default:
        return <CustomerStatistics />;
    }
  };

  return (
    <div className="app">
      <Sidebar onOptionClick={setSelectedOption} />
      <div className="main-content">
        {renderContent()}
      </div>
    </div>
  );
};

export default AdminDashboard;
