import { render, screen } from '@testing-library/react';
import LandingPage from './LandingPage';
import { MemoryRouter } from 'react-router-dom';

describe('LandingPage Component', () => {
  beforeEach(() => {
    render(
      <MemoryRouter>
        <LandingPage />
      </MemoryRouter>
    );
  });

  test('renders carousel with correct text', () => {
    // Check if the carousel caption with "Welcome to Telecom" is in the document
    const carouselCaption = screen.getByText(/Welcome to Telecom/i);
    expect(carouselCaption).toBeInTheDocument();
  });

  test('renders about us section', () => {
    // Check for the About Us heading
    const aboutUsHeading = screen.getByRole('heading', { name: /About Us/i });
    expect(aboutUsHeading).toBeInTheDocument();
  });

  test('renders plan cards with correct test IDs', () => {
    // Use test IDs for plan card testing
    const basicPlan = screen.getByTestId('plan-basic');
    const standardPlan = screen.getByTestId('plan-standard');
    const premiumPlan = screen.getByTestId('plan-premium');

    expect(basicPlan).toBeInTheDocument();
    expect(standardPlan).toBeInTheDocument();
    expect(premiumPlan).toBeInTheDocument();
  });
});
