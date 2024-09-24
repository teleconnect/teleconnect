import React from 'react';
import { render, screen } from '@testing-library/react';
import PlanCard from '../PlanCard';

test('renders plan name and price', () => {
  const plan = {
    name: 'Basic Plan',
    price: '$10/month',
  };
  
  render(<PlanCard plan={plan} />);
  
  // Test if the plan name is displayed
  const planName = screen.getByText(/basic plan/i);
  expect(planName).toBeInTheDocument();
  
  // Test if the plan price is displayed
  const planPrice = screen.getByText(/\$10\/month/i);
  expect(planPrice).toBeInTheDocument();
});

test('displays select button', () => {
  const plan = {
    name: 'Basic Plan',
    price: '$10/month',
  };

  render(<PlanCard plan={plan} />);
  
  const selectButton = screen.getByRole('button', { name: /select/i });
  expect(selectButton).toBeInTheDocument();
});
