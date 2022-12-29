import axios from "axios";

export const whoAmI = (token: string) => {
  return axios.get("http://127.0.0.1:4433/sessions/whoami", {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
};
