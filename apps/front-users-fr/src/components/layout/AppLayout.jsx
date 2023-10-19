import { Outlet } from "react-router-dom";
import { Grid, Header } from "semantic-ui-react";

const AppLayout = () => {
    return (
        <Grid celled>
            <Grid.Row>
            <Header as='h2' icon textAlign='center'>
                <Header.Content>CDC Demo</Header.Content>
                <Header.Content>Front France users</Header.Content>
            </Header>
            </Grid.Row>
            <Grid.Row>
                <Grid.Column width={16}>
                    <Outlet />
                </Grid.Column>
            </Grid.Row>
        </Grid>
    )
};

export default AppLayout;