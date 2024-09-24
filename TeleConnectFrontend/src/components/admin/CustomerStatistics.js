import React, { useEffect, useState } from "react";
import { Pie } from "react-chartjs-2"; // Import the Pie chart component
import { Chart, ArcElement, Tooltip, Legend } from "chart.js"; // Import necessary elements from Chart.js
import "../../styles/admin/CustomerStatistics.css";

// Register the necessary components globally
Chart.register(ArcElement, Tooltip, Legend);

const CustomerStatistics = () => {
  const [data, setData] = useState(null); // State to store the fetched data
  const [loading, setLoading] = useState(true); // State to handle loading status
  const [error, setError] = useState(null); // State to handle errors

  useEffect(() => {
    const fetchStatistics = async () => {
      try {
        const response = await fetch('http://localhost:8083/api/admin/dashboard');
        if (!response.ok) {
          throw new Error('Failed to fetch data');
        }
        const result = await response.json();
        setData(result);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchStatistics();
  }, []); // Empty dependency array ensures it runs only once on mount

  // Display loading or error message
  if (loading) return <div>Loading...</div>;
  if (error) return <div>Error: {error}</div>;

  // Extract values from the fetched data
  const {
    currentActiveUsers,
    totalRegisteredUsers,
    totalOnboardedUsers,
    mobileNumbersAssigned,
    usersWithBasicPlan,
    usersWithStandardPlan,
    usersWithPremiumPlan,
    usersWithVerifiedEmail,
  } = data;

  // Calculate deactivated users
  const deactivatedUsers = totalOnboardedUsers - currentActiveUsers;

  // Pie chart data
  const pieChartData = {
    labels: ["Basic Plan", "Standard Plan", "Premium Plan"],
    datasets: [
      {
        label: `Total users: ${totalOnboardedUsers}`,
        data: [usersWithBasicPlan, usersWithStandardPlan, usersWithPremiumPlan],
        backgroundColor: ["#FF6384", "#36A2EB", "#FFCE56"],
      },
    ],
  };

  return (
    <div className="customer-statistics">
      <div className="top-section">
        <div className="box">
          <div className="label">Users with Verified Email</div>
          <div className="number">{usersWithVerifiedEmail}</div>
        </div>
        <div className="box">
          <div className="label">Total Registered Users</div>
          <div className="number">{totalRegisteredUsers}</div>
        </div>
        <div className="box">
          <div className="label">Total Onboarded Users</div>
          <div className="number">{totalOnboardedUsers}</div>
        </div>
        <div className="box">
          <div className="label">Total Mobile Numbers Assigned</div>
          <div className="number">{mobileNumbersAssigned}</div>
        </div>
      </div>
      <div className="bottom-section">
        <div className="left-section">
          <div className="box">
            <div className="label">Current Active Users</div>
            <div className="number">{currentActiveUsers}</div>
          </div>
          <div className="box">
            <div className="label">Deactivated Users</div>
            <div className="number">{deactivatedUsers}</div>
          </div>
        </div>
        <div className="right-section">
          <h3>Total Users: {totalOnboardedUsers}</h3>
          <Pie data={pieChartData} /> {/* Render the pie chart */}
        </div>
      </div>
    </div>
  );
};

export default CustomerStatistics;
