import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import { Link } from 'react-scroll';
import { Dropdown } from 'react-bootstrap';
import '../styles/Navbar.css';

const Navbar = () => {
  return (
    <nav className="navbar navbar-expand-lg navbar-dark bg-dark">
      <div className="container-fluid">
        <a className="navbar-brand" href="/">TeleConnect</a>
        <button
          className="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarNav"
          aria-controls="navbarNav"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span className="navbar-toggler-icon"></span>
        </button>
        <div className="collapse navbar-collapse" id="navbarNav">
          <ul className="navbar-nav ms-auto">
            <li className="nav-item">
              <Link
                className="nav-link"
                to="plans-section"
                smooth={true}
                duration={500}
                offset={-70} // Adjust offset to consider the navbar height
              >
                Plans
              </Link>
            </li>
            <li className="nav-item">
              <a className="nav-link" href="/email-verification">Register</a>
            </li>
            <li className="nav-item">
              {/* Dropdown for Login */}
              <Dropdown>
                <Dropdown.Toggle className="nav-link btn btn-link text-white" id="dropdownLogin">
                  Login
                </Dropdown.Toggle>
                <Dropdown.Menu>
                  <Dropdown.Item href="/login">User Login</Dropdown.Item>
                  <Dropdown.Item href="/admin-login">Admin Login</Dropdown.Item>
                </Dropdown.Menu>
              </Dropdown>
            </li>
            
          </ul>
        </div>
      </div>
    </nav>
  );
};

export default Navbar;
