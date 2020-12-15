import React from 'react';
import clsx from 'clsx';
import { makeStyles, useTheme } from '@material-ui/core/styles';
import Drawer from '@material-ui/core/Drawer';
import CssBaseline from '@material-ui/core/CssBaseline';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import List from '@material-ui/core/List';
import Typography from '@material-ui/core/Typography';
import Divider from '@material-ui/core/Divider';
import IconButton from '@material-ui/core/IconButton';
import MenuIcon from '@material-ui/icons/Menu';
import ChevronLeftIcon from '@material-ui/icons/ChevronLeft';
import ChevronRightIcon from '@material-ui/icons/ChevronRight';
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import InboxIcon from '@material-ui/icons/MoveToInbox';
import MailIcon from '@material-ui/icons/Mail';
import Solver from './Solver'
import { CsvToHtmlTable } from 'react-csv-to-table';

const drawerWidth = 240;

const useStyles = makeStyles((theme) => ({
  root: {
    display: 'flex',
  },
  appBar: {
    transition: theme.transitions.create(['margin', 'width'], {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.leavingScreen,
    }),
    backgroundColor: '#759EB8',
  },
  appBarShift: {
    width: `calc(100% - ${drawerWidth}px)`,
    marginLeft: drawerWidth,
    transition: theme.transitions.create(['margin', 'width'], {
      easing: theme.transitions.easing.easeOut,
      duration: theme.transitions.duration.enteringScreen,
    }),
  },
  menuButton: {
    marginRight: theme.spacing(2),
  },
  hide: {
    display: 'none',
  },
  drawer: {
    width: drawerWidth,
    flexShrink: 0,
  },
  drawerPaper: {
    width: drawerWidth,
  },
  drawerHeader: {
    display: 'flex',
    alignItems: 'center',
    padding: theme.spacing(0, 1),
    // necessary for content to be below app bar
    ...theme.mixins.toolbar,
    justifyContent: 'flex-end',
  },
  content: {
    flexGrow: 1,
    padding: theme.spacing(3),
    transition: theme.transitions.create('margin', {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.leavingScreen,
    }),
    marginLeft: -drawerWidth,
  },
  contentShift: {
    transition: theme.transitions.create('margin', {
      easing: theme.transitions.easing.easeOut,
      duration: theme.transitions.duration.enteringScreen,
    }),
    marginLeft: 0,
  },
}));

export default function PersistentDrawerLeft() {
  const classes = useStyles();
  const theme = useTheme();
  const [open, setOpen] = React.useState(true);
  const [component, setComponent] = React.useState(0)
  const [csv, setCsv] = React.useState('')
  const [page, setPage] = React.useState({
                            textAreaValue: "",
                            values: [],
                            initialized: false,
                            uploaded: false,
                            generated: false,
                            solved: false,
                            csv: "",
                            numCol: 0,
                            startCol: 0,
                            endCol: 0,
                            weights: "",
                            initializeError: false,
                            uploadedError: false,
                            generatedError: false,
                            solvedError: false,
                          })
    const [textAreaValue, setTextAreaValue] = React.useState('')
    const [values, setValues] = React.useState([])
    const [initialized, setInitialized] = React.useState(false)
    const [numCol, setNumCol] = React.useState(0)


  const handleDrawerOpen = () => {
    setOpen(true);
  };

  const handleDrawerClose = () => {
    setOpen(false);
  };

  const handleCsv = (contents) => {
    setCsv(contents);
  }
  
  const handlePage = (state) => {
      setPage(state);
  }

  const handleNumCol = (num) => {
    setNumCol(num);
  }

  return (
    <div className={classes.root}>
      <CssBaseline />
      <AppBar
        position="fixed"
        className={clsx(classes.appBar, {
          [classes.appBarShift]: open,
        })}
      >
        <Toolbar>
          <IconButton
            color="inherit"
            aria-label="open drawer"
            onClick={handleDrawerOpen}
            edge="start"
            className={clsx(classes.menuButton, open && classes.hide)}
          >
            <MenuIcon />
          </IconButton>
          <Typography variant="h6" noWrap>
            Menu
          </Typography>
        </Toolbar>
      </AppBar>
      <Drawer
        className={classes.drawer}
        variant="persistent"
        anchor="left"
        open={open}
        classes={{
          paper: classes.drawerPaper,
        }}
      >
        <div className={classes.drawerHeader}>
          <IconButton onClick={handleDrawerClose}>
            {theme.direction === 'ltr' ? <ChevronLeftIcon /> : <ChevronRightIcon />}
          </IconButton>
        </div>
        <Divider />
        <List>
          {['Home', 'Solver', 'Solution'].map((text, index) => (
            <ListItem button key={text} onClick={() => setComponent(index)}>
              {/* 
                // TODO: 
                // CHANGE THESE ICONS
              */}
              <ListItemIcon>{index % 2 === 0 ? <InboxIcon /> : <MailIcon />}</ListItemIcon>
              <ListItemText primary={text} />
            </ListItem>
          ))}
        </List>
      </Drawer>
      <main
        className={clsx(classes.content, {
          [classes.contentShift]: open,
        })}
      >
        

        {
            component === 0 ? 
            <div style={{
              fontSize: '1.5em',
              fontFamily: 'Roboto, "Helvetica Neue", Helvetica, Arial, sans-serif',
              fontWeight: '500',
              lineHeight: '1.1',
              padding: '20px',
              width: '100%',
              textAlign: 'left',
              }}>
              <h1>Student Grade Optimizer</h1>
              <Typography>This is an online tool that uses a linear optimization solver to optimize a students final grades</Typography>
              
              <h2>Getting Started</h2>
              <Typography>You can upload any .CSV file in the "Solver" tab to get started and pick your configuration.</Typography>
            </div>
            : null
        }
        
        {
            component === 1 ? 
            <Solver 
            csvHandler={(contents) => handleCsv(contents)} 
            numColHanlder={(input) => handleNumCol(input)} 
            numCol={numCol}
            />
            : null
        }

        {
          component === 2 ?
            csv === '' ? 
              <h1 style={{marginTop: '5%'}}>Not solved yet!</h1>
              :
              <div style={{marginTop: '5%'}}>
                <CsvToHtmlTable
                  data={csv}
                  tableClassName="table striped hover"
                  csvDelimiter=","
                />
              </div>
          :
          null
        }

      </main>
    </div>
  );
}
