import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import Register from '../Register';

test('renders registration form with inputs and submit button', () => {
  render(<Register />);
  
  // Check if name input is rendered
  const nameInput = screen.getByLabelText(/name/i);
  expect(nameInput).toBeInTheDocument();

  // Check if username input is rendered
  const usernameInput = screen.getByLabelText(/username/i);
  expect(usernameInput).toBeInTheDocument();

  // Check if Aadhar upload input is rendered
  const aadharInput = screen.getByLabelText(/aadhar card/i);
  expect(aadharInput).toBeInTheDocument();

  // Check if submit button is rendered
  const submitButton = screen.getByRole('button', { name: /submit/i });
  expect(submitButton).toBeInTheDocument();
});

test('handles form submission', () => {
  const mockSubmit = jest.fn();
  render(<Register onSubmit={mockSubmit} />);
  
  const submitButton = screen.getByRole('button', { name: /submit/i });
  
  // Simulate form submission
  fireEvent.click(submitButton);
  
  expect(mockSubmit).toHaveBeenCalledTimes(1);
});
