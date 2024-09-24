import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import '../styles/EmailVerification.css'; // Import custom styles if needed
// const express = require('express');
// const cors = require('cors');
// const app = express();

// app.use(cors());

const EmailVerification = () => {
  const [email, setEmail] = useState('');
  const [otp, setOtp] = useState('');
  const [otpSent, setOtpSent] = useState(false);
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  const handleSendOtp = async () => {
    try {
      console.log('Sending email:', email); // Debugging line
      await axios.post('http://localhost:8083/api/user/verification/email', 
        { email }, 
        {
          headers: {
            'Content-Type': 'application/json',
          },
          withCredentials: true,  // Ensure cookies (session) are sent along
        }
      );
      
      console.log("OTP sent successfully.");
      setOtpSent(true);
      setError(null);
      alert('OTP has been sent to your email!');
    } catch (err) {
      if (err.response) {
        console.error('Error Response:', err.response.data); // Log the response error
      } else if (err.request) {
        console.error('Error Request:', err.request); // Log the request if no response
      } else {
        console.error('Error Message:', err.message); // Log any other errors
      }
      setError('Failed to send OTP. Please try again.');
    }
};

  
  

const handleVerifyOtp = async () => {
  try {
    // Send the OTP as a query parameter
    await axios.post('http://localhost:8083/api/user/verification/otp', 
      { otp }, 
      {
        headers: {
          'Content-Type': 'application/json',
        },
        withCredentials: true,  // Ensure cookies (session) are sent along
      }
    );
    
    setError(null);
    alert('Email verified successfully!');
    
    // Store email in session storage
    sessionStorage.setItem('verifiedEmail', email);
    navigate('/register'); // Navigate to the registration page
  } catch (err) {
    setError('Invalid OTP. Please try again.');
  }
};


  return (
    <div className="page-wrapper">
      <div className="email-verification-container">
        <h2>Email Verification</h2>
        <div className="form-group">
          <label htmlFor="email">Email</label>
          <input
            type="email"
            id="email"
            className="form-control"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            disabled={otpSent}
            required
          />
          <button
            className="btn btn-primary"
            onClick={handleSendOtp}
            disabled={otpSent || !email}
          >
            Send OTP
          </button>
        </div>

        {otpSent && (
          <div className="form-group">
            <label htmlFor="otp">OTP</label>
            <input
              type="text"
              id="otp"
              className="form-control"
              value={otp}
              onChange={(e) => setOtp(e.target.value)}
              required
            />
            <button
              className="btn btn-success"
              onClick={handleVerifyOtp}
              disabled={!otp}
            >
              Verify
            </button>
          </div>
        )}

        {error && <p className="error-message">{error}</p>}
      </div>
    </div>
  );
};

export default EmailVerification;
