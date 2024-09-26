import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import PlanCard from './PlanCard';
import '../styles/Sim.css';

const plans = [
  {
    title: 'Basic',
    price: 29,
    features: ['1 GB Data', '100 Minutes', '100 SMS'],
    planId: 'plan-basic'
  },
  {
    title: 'Standard',
    price: 49,
    features: ['5 GB Data', '500 Minutes', '500 SMS'],
    planId: 'plan-standard'
  },
  {
    title: 'Premium',
    price: 79,
    features: ['Unlimited Data', 'Unlimited Minutes', 'Unlimited SMS'],
    planId: 'plan-premium'
  }
];

function NewSimPage() {
  const [email, setEmail] = useState('');
  const [eId, setEId] = useState('');
  const [selectedPlan, setSelectedPlan] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    const verifiedEmail = sessionStorage.getItem('verifiedEmail');
    if (verifiedEmail) {
      setEmail(verifiedEmail);
    } else {
      alert('Email not verified. Please verify your email before proceeding.');
      navigate('/email-verification');
    }
  }, [navigate]);

  const handlePlanSelection = (plan) => {
    setSelectedPlan(plan);
  };

  const handleActivatePlan = async () => {
    if (!eId) {
      alert('Please enter your eID before proceeding.');
      return;
    }

    const value = {
      email: email,
      planId: selectedPlan.planId,
      eId: eId
    };

    console.log(value);

    try {
      await axios.post(
        `http://44.201.255.255:8081/api/user/plan/assign`,
        value,  // Pass the request body with the necessary data
        { withCredentials: true }
      );
      alert('Plan assigned successfully.');
      navigate(`/home`);
    } catch (error) {
      console.error('Error assigning plan:', error);
      alert('There was an issue assigning the plan. Please try again.');
    }
  };

  return (
    <div className="sim-page-container">
      <h2>Get New SIM</h2>
      <p>Email: {email}</p>

      <div className="form-group">
        <label htmlFor="eId">eID</label>
        <input
          type="text"
          id="eId"
          name="eId"
          value={eId}
          onChange={(e) => setEId(e.target.value)}
          placeholder="Enter your eID"
        />
      </div>

      <h3>Select a Plan</h3>
      <div className="plan-selection">
        {plans.map(plan => (
          <PlanCard
            key={plan.planId}
            title={plan.title}
            price={plan.price}
            features={plan.features}
            onClick={() => handlePlanSelection(plan)}
          />
        ))}
      </div>

      {selectedPlan && (
        <div className="selected-plan">
          <p>You have selected: {selectedPlan.title}</p>
          <button className="btn btn-success" onClick={handleActivatePlan}>
            Activate Plan
          </button>
        </div>
      )}
    </div>
  );
}

export default NewSimPage;
