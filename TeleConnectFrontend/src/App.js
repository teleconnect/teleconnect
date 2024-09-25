import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Register from './components/Register';
import Login from './components/Login';
import "./App.css";
import LandingPage from './components/LandingPage';
import EmailVerification from './components/EmailVerification';
import Dashboard from './components/Dashboard';
import NewSimPage from './components/NewSimPage';
import ExistingUserPage from './components/ExistingUserPage';
import PlanSummary from './components/PlanSummary';
import AdminLogin from './components/admin/AdminLogin';
import AdminDashboard from './components/admin/AdminDashboard';
import RechargePlan from './components/RechargePlan';

function App() {
  return (
    <Router>
      {}
      <div className="App">
        <Routes>
          <Route path="/register" element={<Register />} />
          <Route path="/login" element={<Login />} />
          <Route path="/home" element={<LandingPage />} />
          <Route path="/" element={<LandingPage />} />
          <Route path="/email-verification" element={<EmailVerification />} />
          <Route path="/dashboard" element={<Dashboard />} />
          <Route path="/new-sim" element={<NewSimPage />} />
        <Route path="/existing-user" element={<ExistingUserPage />} />
        <Route path="/recharge-plan" element={<RechargePlan />} />
        <Route path="/plan-summary/:plan" element={<PlanSummary />} />
        <Route path="/admin-login" element={<AdminLogin />} />
        <Route path="/admin-dashboard" element={<AdminDashboard />} />
        



          
        </Routes>
      </div>
    </Router>
  );
}

export default App;
