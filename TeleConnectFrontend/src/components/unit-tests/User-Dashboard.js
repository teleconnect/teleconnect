import React from 'react';
import { render, screen } from '@testing-library/react';
import UserDashboard from '../UserDashboard';
import { MemoryRouter } from 'react-router-dom';


test('renders welcome message for user', () => {
  const user = {
    username: 'john_doe',
  };
  
  render(<UserDashboard user={user} />);
  
  const welcomeMessage = screen.getByText(/welcome, john_doe/i);
  expect(welcomeMessage).toBeInTheDocument();
});

test('displays user details section', () => {
  const user = {
    username: 'john_doe',
    email: 'john_doe@example.com',
  };

  render(<UserDashboard user={user} />);
  
  const userDetails = screen.getByText(/john_doe@example.com/i);
  expect(userDetails).toBeInTheDocument();
});
