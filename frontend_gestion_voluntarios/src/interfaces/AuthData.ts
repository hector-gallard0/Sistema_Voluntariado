import TokenPayload from "./TokenPayload";

export default interface AuthData {
    token: string,
    tokenPayload: TokenPayload
}