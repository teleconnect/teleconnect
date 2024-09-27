import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import PlanCard from '../components/PlanCard'; // Adjust the path according to your structure
import axios from 'axios';
import '../styles/Sim.css';

function ExistingUserPage() {
  const [email, setEmail] = useState('');
  const [selectedPlan, setSelectedPlan] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    const verifiedEmail = sessionStorage.getItem('verifiedEmail');

    if (verifiedEmail) {
      setEmail(verifiedEmail);
    } else {
      alert('User details not found. Please log in again.');
      navigate('/login'); // Redirect to login if user details are not present
    }
  }, [navigate]);

  const handlePlanSelection = (plan) => {
    setSelectedPlan(plan);
  };

  const handleActivatePlan = async () => {
    if (selectedPlan) {
      try {
        // Sending a PUT request with a request body containing the email and planId
        const response = await axios.put(
          `http://44.201.255.255:8081/api/user/plan/change`,
          {
            email: email,
            planId: selectedPlan.planId
          },
          { withCredentials: true }
        );

        // Alert the response message directly
        alert(response.data); // Alert the message received from the backend
        navigate(`/home`); // Redirect to plan summary page
      } catch (error) {
        // In case of an error, alert the error message from the response
        const errorMessage = error.response?.data || 'There was an issue changing the plan. Please try again.';
        alert(errorMessage); // Alert the user with the error message
      }
    }
  };

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

  return (
    <div className="sim-page-container">
      <h2>Welcome Back!</h2>
      <p>Email: {email}</p>

      <h3>Change Plan</h3>
      <div className="plan-selection">
        {plans.map((plan) => (
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

export default ExistingUserPage;
