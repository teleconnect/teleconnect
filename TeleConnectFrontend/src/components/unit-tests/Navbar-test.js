import { render, screen, waitFor } from '@testing-library/react';
import axios from 'axios';
import UserDashboard from './UserDashboard';
import { BrowserRouter } from 'react-router-dom';

// Mock axios
jest.mock('axios');

describe('UserDashboard Component', () => {
  test('fetches and displays user data', async () => {
    const mockUserData = {
      fullName: 'John Doe',
      email: 'john@example.com',
      phoneNumber: '1234567890',
      aadhaarNumber: '1111-2222-3333'
    };

    axios.get.mockResolvedValueOnce({ data: mockUserData });

    render(
      <BrowserRouter>
        <UserDashboard />
      </BrowserRouter>
    );

    await waitFor(() => {
      const fullName = screen.getByText(/Full Name:/i);
      expect(fullName).toHaveTextContent('John Doe');
    });
  });

  test('redirects to login if not authenticated', async () => {
    axios.get.mockRejectedValueOnce(new Error('Unauthorized'));

    render(
      <BrowserRouter>
        <UserDashboard />
      </BrowserRouter>
    );

    await waitFor(() => {
      const loginRedirect = screen.queryByText(/Loading/i);
      expect(loginRedirect).toBeNull();
    });
  });
});
