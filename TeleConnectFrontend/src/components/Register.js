import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

function Register() {
  const [firstName, setFirstName] = useState(''); 
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [mobileNumber, setMobileNumber] = useState(''); 
  const [aadharNumber, setAadharNumber] = useState(''); // Correct name matching DTO
  const [aadharImage, setAadharImage] = useState(null); // Correct name matching DTO
  const navigate = useNavigate();

  // Get the verified email from sessionStorage and set it as the default email
  useEffect(() => {
    const verifiedEmail = sessionStorage.getItem('verifiedEmail');
    if (verifiedEmail) {
      setEmail(verifiedEmail);
    } else {
      alert('Email not verified. Please verify your email before registration.');
      navigate('/email-verification');
    }
  }, [navigate]);

  // Handle Aadhaar file change
  const handleAadharImageChange = (event) => {
    const file = event.target.files[0];
    const fileType = file.type;

    // Validate file type
    if (fileType === 'image/jpeg' || fileType === 'image/png') {
      setAadharImage(file);
    } else {
      alert('Only JPG and PNG files are allowed.');
      event.target.value = ''; // Clear the file input if invalid
    }
  };

  // Handle form submission
  const handleRegister = async (event) => {
    event.preventDefault();

    if (!aadharImage) {
      alert('Please upload your Aadhaar file.');
      return;
    }

    try {
      const formData = new FormData();
      formData.append('firstName', firstName.trim()); 
      formData.append('email', email.trim());
      formData.append('password', password.trim());
      formData.append('mobileNumber', mobileNumber.trim());
      formData.append('aadharNumber', aadharNumber.trim()); // Correct name matching DTO
      formData.append('aadharImage', aadharImage); // Correct name matching DTO

      const response = await axios.post('http://44.201.255.255:8081/api/user/register', formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      });

      console.log(response.data);
     alert('Registration successful!');
      navigate('/login');
    } catch (error) {
      console.error('Error submitting form:', error);
      alert(error);
    }
  };

  return (
    <div className="whole">
      <div className="form-container">
        <h1>Registration</h1>
        <form onSubmit={handleRegister}>
          <div className="form-group">
            <label>First Name</label>
            <input
              type="text"
              id="firstName"
              name="firstName"
              placeholder="Enter First Name"
              value={firstName}
              onChange={(event) => setFirstName(event.target.value)}
              required
            />
          </div>
          <div className="form-group">
            <label>Email</label>
            <input
              type="email"
              id="email"
              name="email"
              placeholder="Enter Email"
              value={email}
              required
              readOnly
            />
          </div>
          <div className="form-group">
            <label>Aadhaar Number</label>
            <input
              type="text"
              id="aadharNumber"
              name="aadharNumber" // Matching DTO field
              placeholder="Enter Aadhaar number"
              value={aadharNumber}
              onChange={(event) => setAadharNumber(event.target.value)}
              required
            />
          </div>
          <div className="form-group">
            <label>Mobile Number</label>
            <input
              type="text"
              id="mobileNumber"
              name="mobileNumber"
              placeholder="Enter Mobile number"
              value={mobileNumber}
              onChange={(event) => setMobileNumber(event.target.value)}
              required
            />
          </div>
          <div className="form-group">
            <label>Password</label>
            <input
              type="password"
              id="password"
              name="password"
              placeholder="Enter password"
              value={password}
              onChange={(event) => setPassword(event.target.value)}
              required
            />
          </div>
          <div className="form-group">
            <label>Upload Aadhaar</label>
            <input
              type="file"
              id="aadharImage"
              name="aadharImage" // Correct name matching DTO
              onChange={handleAadharImageChange}
              required
            />
          </div>
          <button type="submit" className="btn btn-primary">Register</button>
        </form>
      </div>
    </div>
  );
}

export default Register;
