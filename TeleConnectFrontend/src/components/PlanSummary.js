import React from 'react';
import { useParams } from 'react-router-dom';
import '../styles/Sim.css';

function PlanSummary() {
  const { plan } = useParams(); // Getting the selected plan from URL params

  return (
    <div className="plan-summary">
      <h2>Plan Summary</h2>
      <p>You have selected: {plan}</p>
      {/* Display more details or options related to the plan */}
    </div>
  );
}

export default PlanSummary;
