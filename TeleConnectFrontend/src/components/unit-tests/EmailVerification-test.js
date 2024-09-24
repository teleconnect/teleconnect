import { render, screen, fireEvent } from '@testing-library/react';
import axios from 'axios';
import EmailVerification from './EmailVerification';

// Mock axios
jest.mock('axios');

describe('EmailVerification Component', () => {
  test('renders email input and button', () => {
    render(<EmailVerification />);
    const emailInput = screen.getByLabelText(/Email/i);
    const sendOtpButton = screen.getByText(/Send OTP/i);

    expect(emailInput).toBeInTheDocument();
    expect(sendOtpButton).toBeInTheDocument();
  });

  test('sends OTP and shows OTP input on success', async () => {
    axios.post.mockResolvedValueOnce({});
    render(<EmailVerification />);
    
    const emailInput = screen.getByLabelText(/Email/i);
    const sendOtpButton = screen.getByText(/Send OTP/i);

    fireEvent.change(emailInput, { target: { value: 'test@example.com' } });
    fireEvent.click(sendOtpButton);

    // Mock async OTP send response
    expect(axios.post).toHaveBeenCalledWith('/api/send-otp', { email: 'test@example.com' });

    // Simulate showing OTP input
    const otpInput = await screen.findByLabelText(/OTP/i);
    expect(otpInput).toBeInTheDocument();
  });

  test('shows error on failed OTP send', async () => {
    axios.post.mockRejectedValueOnce(new Error('Failed to send OTP'));
    render(<EmailVerification />);

    const emailInput = screen.getByLabelText(/Email/i);
    const sendOtpButton = screen.getByText(/Send OTP/i);

    fireEvent.change(emailInput, { target: { value: 'test@example.com' } });
    fireEvent.click(sendOtpButton);

    const errorMessage = await screen.findByText(/Failed to send OTP/i);
    expect(errorMessage).toBeInTheDocument();
  });
});
