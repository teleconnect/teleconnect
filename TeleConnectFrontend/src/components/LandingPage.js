import React from 'react';
import Navbar from './Navbar';
import '../styles/LandingPage.css';
import '../styles/AboutUs.css'; // Import the About Us styles
import '../styles/Footer.css'
import { Element } from 'react-scroll'; // Import Element from react-scroll

import image1 from '../images/3.jpg';
import image2 from '../images/2.jpg';
import image3 from '../images/1.jpg';

// PlanCard Component (with no button)
const PlanCard = ({ title, price, description, features }) => {
  return (
    <div className="card">
      <div className="card-body">
        <h3 className="card-title">{title}</h3>
        <h4 className="card-price">{price}</h4>
        <p className="card-description">{description}</p>
        <ul className="card-features">
          {features.map((feature, index) => (
            <li key={index}>{feature}</li>
          ))}
        </ul>
      </div>
    </div>
  );
};

const LandingPage = () => {
  return (
    <>
      <Navbar />

      {/* Carousel Section */}
      <div id="carouselExampleIndicators" className="carousel slide" data-bs-ride="carousel">
        <div className="carousel-indicators">
          <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="0" className="active" aria-current="true" aria-label="Slide 1"></button>
          <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="1" aria-label="Slide 2"></button>
          <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="2" aria-label="Slide 3"></button>
        </div>
        <div className="carousel-inner">
          <div className="carousel-item active">
            <img src={image1} className="d-block w-100" alt="Welcome to Telecom" />
            <div className="carousel-caption d-none d-md-block">
              <h1>Welcome to TeleConnect</h1>
              <p>Your trusted partner in seamless communication services.</p>
            </div>
          </div>
          <div className="carousel-item">
            <img src={image2} className="d-block w-100" alt="Explore Our Plans" />
          </div>
          <div className="carousel-item">
            <img src={image3} className="d-block w-100" alt="Join Us Today" />
          </div>
        </div>
        <button className="carousel-control-prev" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="prev">
          <span className="carousel-control-prev-icon" aria-hidden="true"></span>
          <span className="visually-hidden">Previous</span>
        </button>
        <button className="carousel-control-next" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="next">
          <span className="carousel-control-next-icon" aria-hidden="true"></span>
          <span className="visually-hidden">Next</span>
        </button>
      </div>

      <br />
      <br />
      <br />

      {/* About Us Section */}
      <div className="about-us-container">
        <h1>About Us</h1>
        <p>
          Welcome to Telecom, your trusted partner in seamless communication services. We are committed to providing our customers with the best in mobile, internet, and communication technologies, ensuring you stay connected wherever you are.
        </p>
        <h2>Our Mission</h2>
        <p>
          Our mission is to deliver high-quality, reliable, and affordable communication solutions that empower our customers to connect, share, and communicate effortlessly. We believe in innovation, customer satisfaction, and building a network that supports your everyday needs.
        </p>
        <h2>Our Vision</h2>
        <p>
          We envision a world where everyone has access to advanced communication technologies that enhance their lives and connect them with the people and information that matter most. At Telecom, we strive to be at the forefront of this digital revolution, continually pushing the boundaries of what's possible.
        </p>
        <h2>Why Choose Us?</h2>
        <ul>
          <li>Reliable and fast network coverage</li>
          <li>Affordable pricing plans</li>
          <li>Exceptional customer service</li>
          <li>Innovative products and services</li>
          <li>Commitment to sustainability and community support</li>
        </ul>
        <p>
          Join us on this journey to better communication. Whether you're looking for mobile plans, internet services, or the latest in communication technology, Telecom is here to serve you.
        </p>
        <p>
          Thank you for choosing Telecom, where your connection matters.
        </p>
      </div>

      <br />
      <br />
      <br />

      {/* Plans Section */}
      <div className="container mt-5">
        <Element name="plans-section">
          {/* Wrap the plans section in an Element */}
          <h1 className="text-center mb-4">Choose Your Plan</h1>
          <div className="row">
            {[
              {
                title: "Basic",
                price: "₹500",
                description: "A great start for individual users.",
                features: ["1 GB/day Data Usage", "100 Free SMS/day", "Unlimited Calls","Standard Network Speed"]
              },
              {
                title: "Standard",
                price: "₹1000",
                description: "Best for small teams.",
                features: ["2 GB/day Data Usage", "200 Free SMS/day", "Unlimited Calls", "Faster Network Speed"]
              },
              {
                title: "Premium",
                price: "₹2000",
                description: "Full features for enterprises.",
                features: ["5 GB/day Data Usage", "Unlimited SMS", "Highest Network Speed", "International Roaming"]
              }
            ].map((plan, index) => (
              <div className="col-md-4 mb-4" key={index} data-testid={`plan-${plan.title.toLowerCase()}`}>
                <PlanCard
                  title={plan.title}
                  price={plan.price}
                  description={plan.description}
                  features={plan.features}
                />
              </div>
            ))}
          </div>
        </Element>
      </div>

      <br />
      <br />
      <br />

      {/* Footer section */}
      <footer className="footer">
        <div className="container">
          <div className="row">
            <div className="col-md-4">
              <h5>About Us</h5>
              <p>
                We are committed to providing our customers with the best in mobile, internet, and communication technologies, ensuring you stay connected wherever you are.
              </p>
            </div>
            <div className="col-md-4">
              <h5>Contact Us</h5>
              <p>Email: support@telecom.com</p>
              <p>Phone: +123 456 7890</p>
              <p>Address: 123 Telecom Street, Tech City, TX</p>
            </div>
            <div className="col-md-4">
              <h5>Follow Us</h5>
              <ul className="social-links">
                <li><a href="#">Facebook</a></li>
                <li><a href="#">Twitter</a></li>
                <li><a href="#">Instagram</a></li>
                <li><a href="#">LinkedIn</a></li>
              </ul>
            </div>
          </div>
          <hr />
          <div className="text-center">
            <p>&copy; {new Date().getFullYear()} Telecom. All rights reserved.</p>
          </div>
        </div>
      </footer>
    </>
  );
};

export default LandingPage;
