import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../styles/Dashboard.css';

function Dashboard() {
  const [selectedOption, setSelectedOption] = useState(null);
  const navigate = useNavigate(); // Use navigate from React Router

  const handleOptionClick = (option) => {
    setSelectedOption(option);

    // Redirect based on option selected
    if (option === 'newSim') {
      navigate('/new-sim'); // Redirect to Get New SIM page
    } else if (option === 'existingSim') {
      navigate('/existing-user'); // Redirect to Existing User page
    }
  };

  return (
    <div className="center-screen">
      <div className="dashboard-container">
        <h2>Welcome to Your Dashboard</h2>
        <p>Please select one of the options below:</p>

        <div className="option-buttons">
          <button onClick={() => handleOptionClick('newSim')} className="option-btn">
            Get New SIM
          </button>
          <button onClick={() => handleOptionClick('existingSim')} className="option-btn">
            Already Have a SIM
          </button>
        </div>

        {selectedOption && (
          <div className="option-details">
            {selectedOption === 'newSim' ? (
              <p>You have selected: Get New SIM. Redirecting to the new SIM registration page...</p>
            ) : (
              <p>You have selected: Already Have a SIM. Redirecting to the activation page...</p>
            )}
          </div>
        )}
      </div>
    </div>
  );
}

export default Dashboard;
