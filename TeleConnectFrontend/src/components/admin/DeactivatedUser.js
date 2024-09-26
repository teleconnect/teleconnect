import React, { useEffect, useState } from "react";
import axios from "axios";
import "../../styles/admin/Admin.css";

const ActivatedUsers = () => {
  const [users, setUsers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    axios
      .get("http://44.201.255.255:8081/api/admin/deactivatedUsers")
      .then((response) => {
        setUsers(response.data);
        setLoading(false);
      })
      .catch((err) => {
        setError(err.message);
        setLoading(false);
      });
  }, []);

  if (loading) {
    return <p>Loading...</p>;
  }

  if (error) {
    return <p>Error: {error}</p>;
  }

  return (
    <div>
      <h1>Dectivated Users</h1>
      <p>This section displays a list of users with deactive plans.</p>
      <ul>
        {users.length > 0 ? (
          users.map((user, index) => (
            <li key={index}>
              Email: {user.email} - Mobile: {user.mobileNumber}
            </li>
          ))
        ) : (
          <p>No activated users found.</p>
        )}
      </ul>
    </div>
  );
};

export default ActivatedUsers;
