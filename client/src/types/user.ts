export type LoginUser = {
  id: number;
  realName: string;
  email: string;
  displayName: string;
  homepageKey: string;
  userKey: string;
  createdAt: string;
};

export type UserResponse = {
  id: number;
  email: string;
  displayName: string;
  homepageKey: string;
  createdAt: string;
};