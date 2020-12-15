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

  const [textAreaValue, setTextAreaValue] = React.useState("")
  const [values, setValues] = React.useState([])
  const [weights, setWeights] = React.useState("")

  const [numCol, setNumCol] = React.useState(0)
  const [startCol, setStartCol] = React.useState(0)
  const [endCol, setEndCol] = React.useState(0)
  
  const [initialized, setInitialized] = React.useState(false)
  const [uploaded, setUploaded] = React.useState(false)
  const [generated, setGenerated] = React.useState(false)
  const [solved, setSolved] = React.useState(false)
  const [initializeError, setInitializeError] = React.useState(false)
  const [uploadedError, setUploadedError] = React.useState(false)
  const [generatedError, setGeneratedError] = React.useState(false)
  const [solvedError, setSolvedError] = React.useState(false)

  const handleDrawerOpen = () => {
    setOpen(true);
  };

  const handleDrawerClose = () => {
    setOpen(false);
  };

  const handleCsv = (contents) => {
    setCsv(contents);
  }
  
  const handleNumCol = (num) => {
    setNumCol(num);
  }

  const handleStartCol = (num) => {
    setStartCol(num);
  }
  
  const handleEndCol = (num) => {
    setEndCol(num);
  }

  const hanldeInitialized = (input) => {
    setInitialized(input);
  }

  const hanldeUploaded = (input) => {
    setUploaded(input);
  }  

  const hanldeGenerated= (input) => {
    setGenerated(input);
  }  

  const hanldeSolved= (input) => {
    setSolved(input);
  } 
  
  const hanldeInitializedError= (input) => {
    setInitializeError(input);
  }

  const hanldeUploadedError= (input) => {
    setUploadedError(input);
  }

  const hanldeGeneratedError = (input) => {
    setGeneratedError(input);
  }

  const hanldeSolvedError= (input) => {
    setSolvedError(input);
  }

  const hanldeTextAreaValue= (input) => {
    setTextAreaValue(input);
  }

  const hanldeValues= (input) => {
    setValues(input);
  }

  const hanldeWeights= (input) => {
    setWeights(input);
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
              numColHandler={(input) => handleNumCol(input)} 
              startColHandler={(input) => handleStartCol(input)} 
              endColHandler={(input) => handleEndCol(input)} 

              initializedHandler={(input) => hanldeInitialized(input)} 
              uploadedHandler={(input) => hanldeUploaded(input)} 
              generatedHandler={(input) => hanldeGenerated(input)}
              solvedHandler={(input) => hanldeSolved(input)}

              initializedErrorHandler={(input) => hanldeInitializedError(input)} 
              uploadedErrorHandler={(input) => hanldeUploadedError(input)}
              generatedErrorHandler={(input) => hanldeGeneratedError(input)}
              solvedErrorHandler={(input) => hanldeSolvedError(input)}
              
              csv={csv}
              numCol={numCol}
              startCol={startCol}
              endCol={endCol}
              initialized={initialized}
              uploaded={uploaded}
              generated={generated}
              solved={solved}
              initializeError={initializeError}
              uploadedError={uploadedError}
              generatedError={generatedError}
              solvedError={solvedError}

              textAreaValue={textAreaValue}
              values={values}
              weights={weights}

              textAreaValueHandler={(input) => hanldeTextAreaValue(input)}
              valuesHandler={(input) => hanldeValues(input)}
              weightsHandler={(input) => hanldeWeights(input)}
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
