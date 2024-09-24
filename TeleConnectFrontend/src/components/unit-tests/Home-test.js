import { render, screen } from '@testing-library/react';
import Home from './Home';

describe('Home Component', () => {
  test('renders welcome message', () => {
    render(<Home />);
    const welcomeMessage = screen.getByText(/Welcome to the Home Page!/i);
    expect(welcomeMessage).toBeInTheDocument();
  });
});
