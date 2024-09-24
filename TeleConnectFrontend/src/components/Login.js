import React from 'react';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import * as Yup from 'yup';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';


const Login = () => {
  const navigate = useNavigate();

  const initialValues = {
    email: '',
    password: '',
  };

  const validationSchema = Yup.object({
    email: Yup.string().email('Invalid email address').required('Email is required'),
    password: Yup.string().required('Password is required'),
  });

  const onSubmit = async (values) => {
    console.log('Login form submitted with:', values);
    try {
      const response = await axios.post(
        `http://localhost:8083/api/user/login`,
        values, 
        { withCredentials: true, headers: { 'Content-Type': 'application/json' } } 
      );
      console.log(response.data);
      localStorage.setItem('token', response.data.token);  // Save token in localStorage if provided
      navigate('/dashboard');  // Redirect to the dashboard
    } catch (error) {
      console.error('There was an error logging in!', error.response?.data || error.message);
    }
  };

  return (
    <div className="whole">
      <div className="form-container">
        <h2>Login</h2>
        <Formik initialValues={initialValues} validationSchema={validationSchema} onSubmit={onSubmit}>
          <Form>
            <div className="form-group">
              <label htmlFor="email">Email</label>
              <Field type="email" id="email" name="email" placeholder="Enter your email" />
              <ErrorMessage name="email" component="div" className="error-message" />
            </div>

            <div className="form-group">
              <label htmlFor="password">Password</label>
              <Field type="password" id="password" name="password" placeholder="Enter your password" />
              <ErrorMessage name="password" component="div" className="error-message" />
            </div>
            <button className="btn btn-primary" type="submit">Login</button>
          </Form>
        </Formik>
      </div>
    </div>
  );
};

export default Login;
