db.createUser(
    {
      user: "usr-mongo",
      pwd: "pwd-mongo",
      roles: [
            {
              role: "readWrite",
              db: "dev-mongo"
            }
        ]
    }
);