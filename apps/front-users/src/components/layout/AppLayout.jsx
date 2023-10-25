import { Outlet } from "react-router-dom";
import { Grid, Header } from "semantic-ui-react";

const AppLayout = () => {
  return (
    <Grid celled>
      <Grid.Row>
        <Header as="h1" icon textAlign="center">
          <Header.Content>Users</Header.Content>
        </Header>
      </Grid.Row>
      <Grid.Row>
        <Grid.Column width={16}>
          <Outlet />
        </Grid.Column>
      </Grid.Row>
    </Grid>
  );
};

export default AppLayout;
