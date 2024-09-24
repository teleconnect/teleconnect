import React from 'react';
import '../styles/PlanCard.css';

const PlanCard = ({ title, price, features, onClick }) => {
  return (
    <div className="card shadow-sm">
      <div className="card-body text-center">
        <h5 className="card-title">{title}</h5>
        <h6 className="card-price">₹{price}/mo</h6>
        <ul className="list-unstyled mt-3 mb-4">
          {features.map((feature, index) => (
            <li key={index}>{feature}</li>
          ))}
        </ul>
        <button className="btn btn-primary" onClick={onClick}>
          Choose Plan
        </button>
      </div>
    </div>
  );
};
export default PlanCard;