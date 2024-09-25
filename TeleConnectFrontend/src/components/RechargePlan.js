import React, { useState } from 'react';
import axios from 'axios';
import '../styles/RechargePlan.css'; // Ensure you have this CSS file for styles

const RechargePlan = () => {
  const [email, setEmail] = useState('');
  const [planId, setPlanId] = useState(''); // New state for plan ID
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState(null);
  const [error, setError] = useState(null);

  const handleRecharge = async (e) => {
    e.preventDefault();
    setLoading(true);
    setMessage(null);
    setError(null);

    try {
      // Prepare data for API call
      const requestData = {
        email,
        planId,
      };

      const response = await axios.post("http://localhost:8083/api/user/plan/recharge", requestData);
      setMessage(`Recharge successful for ${email} with Plan ID: ${planId}`);
      setEmail('');
      setPlanId('');
    } catch (err) {
      setError(err.response?.data?.message || "Error recharging plan.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="recharge-plan-container">
      <h2>Recharge Your Plan</h2>
      <form onSubmit={handleRecharge}>
        <label htmlFor="email">Email:</label>
        <input
          type="email"
          id="email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          required
          placeholder="Enter your email"
        />
        
        <label htmlFor="planId">Plan ID:</label>
        <input
          type="text"
          id="planId"
          value={planId}
          onChange={(e) => setPlanId(e.target.value)}
          required
          placeholder="Enter plan ID (e.g., plan-standard)"
        />
        
        <button type="submit" disabled={loading}>
          {loading ? "Recharging..." : "Recharge"}
        </button>
      </form>

      {/* Display success or error messages */}
      {message && <p className="success">{message}</p>}
      {error && <p className="error">{error}</p>}
    </div>
  );
};

export default RechargePlan;
