import { render, screen, fireEvent } from '@testing-library/react';
import Login from './Login';
import axios from 'axios';
import { BrowserRouter } from 'react-router-dom';

// Mock axios
jest.mock('axios');

describe('Login Component', () => {
  test('renders login form', () => {
    render(
      <BrowserRouter>
        <Login />
      </BrowserRouter>
    );
    
    const emailInput = screen.getByLabelText(/Email/i);
    const passwordInput = screen.getByLabelText(/Password/i);
    const loginButton = screen.getByText(/Login/i);

    expect(emailInput).toBeInTheDocument();
    expect(passwordInput).toBeInTheDocument();
    expect(loginButton).toBeInTheDocument();
  });

  test('submits login form successfully', async () => {
    axios.post.mockResolvedValueOnce({
      data: { token: 'dummyToken' }
    });

    render(
      <BrowserRouter>
        <Login />
      </BrowserRouter>
    );

    fireEvent.change(screen.getByLabelText(/Email/i), { target: { value: 'test@example.com' } });
    fireEvent.change(screen.getByLabelText(/Password/i), { target: { value: 'password123' } });

    fireEvent.click(screen.getByText(/Login/i));

    expect(axios.post).toHaveBeenCalledWith('/api/login', {
      email: 'test@example.com',
      password: 'password123'
    });
  });
});
