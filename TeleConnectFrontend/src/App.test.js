import { BrowserRouter } from 'react-router-dom';
import { MemoryRouter } from 'react-router-dom';
import { render, screen } from '@testing-library/react';
import App from './App';
import LandingPage from './components/LandingPage';

describe('LandingPage Component', () => {                                     //PASSING
  test('renders the Plans section with correct plan cards', () => {
    render(
      <MemoryRouter>
        <LandingPage />
      </MemoryRouter>
    );

    const basicPlanCard = screen.getByTestId('plan-basic');
    const standardPlanCard = screen.getByTestId('plan-standard');
    const premiumPlanCard = screen.getByTestId('plan-premium');

    expect(basicPlanCard).toBeInTheDocument();
    expect(standardPlanCard).toBeInTheDocument();
    expect(premiumPlanCard).toBeInTheDocument();
  });
});


test('renders Register page link', () => {             //PASSING
  render(<App />);
  const registerLink = screen.getByText(/Register/i);
  expect(registerLink).toBeInTheDocument();
});

test('renders Login page link', () => {                //PASSING
  render(<App />);
  const loginLink = screen.getByText(/Login/i);
  expect(loginLink).toBeInTheDocument();
});

// test('renders Email Verification page link', () => {
//   // Use MemoryRouter to render the App with routing if required
//   render(
//     <MemoryRouter>
//       <App />
//     </MemoryRouter>
//   );

//   // Try to locate the "Email Verification" link by text
//   const emailVerificationLink = screen.getByText(/Email Verification/i);

//   // Ensure that it's found in the document
//   expect(emailVerificationLink).toBeInTheDocument();
// });


// test('renders User Dashboard page link', () => {             //FAIL
//   render(
//     <MemoryRouter initialEntries={['/ud']}>
//       <App />
//     </MemoryRouter>
//   );
//   const userDashboardLink = screen.getByText(/User Dashboard/i);
//   expect(userDashboardLink).toBeInTheDocument();
// });