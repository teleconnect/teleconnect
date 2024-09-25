import React, { useState } from "react";
import axios from "axios";
import "../../styles/admin/Admin.css";

const DeleteUser = () => {
  const [email, setEmail] = useState("");
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState(null);
  const [error, setError] = useState(null);

  const handleDelete = async (e) => {
    e.preventDefault();
    setLoading(true);
    setMessage(null);
    setError(null);

    try {
      await axios.delete("http://localhost:8083/api/admin/delete-user", {
        data: { email },
      });
      setMessage(`User with email ${email} was deleted successfully.`);
      setEmail("");
    } catch (err) {
      setError(err.response?.data?.message || "Error deleting user.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="delete-user-container">
      <h1>Delete User</h1>
      <form onSubmit={handleDelete}>
        <label>Email of user to delete:</label>
        <input
          type="email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          required
          placeholder="Enter user's email"
        />
        <button type="submit" disabled={loading}>
          {loading ? "Deleting..." : "Delete User"}
        </button>
      </form>

      {/* Display success or error messages */}
      {message && <p className="success">{message}</p>}
      {error && <p className="error">{error}</p>}
    </div>
  );
};

export default DeleteUser;
