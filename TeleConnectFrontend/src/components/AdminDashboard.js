import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Pie } from 'react-chartjs-2';
import 'chart.js/auto';
import "../styles/AdminDashboard.css";

const AdminDashboard = () => {
  const [chartData, setChartData] = useState({
    currentActiveUsers: 0,
    usersWithBasicPlan: 0,
    usersWithStandardPlan: 0,
    usersWithPremiumPlan: 0
  });

  useEffect(() => {
    // Fetch data from the API
    axios.get('http://localhost:8083/api/admin/dashboard')
      .then((response) => {
        const data = response.data;
        setChartData({
          currentActiveUsers: data.currentActiveUsers,
          usersWithBasicPlan: data.usersWithBasicPlan,
          usersWithStandardPlan: data.usersWithStandardPlan,
          usersWithPremiumPlan: data.usersWithPremiumPlan,
        });
      })
      .catch((error) => {
        console.error("There was an error fetching the dashboard data!", error);
      });
  }, []);

  const pieChartData = {
    labels: ['Active Users', 'Basic Plan', 'Standard Plan', 'Premium Plan'],
    datasets: [
      {
        label: 'Users Distribution',
        data: [
          chartData.currentActiveUsers,
          chartData.usersWithBasicPlan,
          chartData.usersWithStandardPlan,
          chartData.usersWithPremiumPlan,
        ],
        backgroundColor: ['#36A2EB', '#FFCE56', '#FF6384', '#4BC0C0'],
        hoverBackgroundColor: ['#36A2EB', '#FFCE56', '#FF6384', '#4BC0C0'],
      },
    ],
  };

  return (
    <div className="dashboard-container">
      <h2>Admin Dashboard</h2>
      <div className="chart-container" style={{ width: '50%', margin: 'auto' }}>
        <Pie data={pieChartData} />
      </div>
    </div>
  );
};

export default AdminDashboard;
